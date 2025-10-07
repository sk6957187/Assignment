import { createContext, useEffect, useState } from "react";
import { fetchFoodList as fetchFoodListAPI } from "../service/FoodServices";
import axios from "axios";

export const StoreContext = createContext(null);

export const StoreProvider = ({ children }) => {
  const [foodList, setFoodList] = useState([]);
  const [quantities, setQuantities] = useState({});
  const [token, setToken] = useState(null);

  // Load food list from backend
  const loadFoodList = async () => {
    try {
      const response = await fetchFoodListAPI();
      setFoodList(response?.data || response);
    } catch (error) {
      console.error("Error fetching food list:", error);
    }
  };

  // Load cart data for logged-in user
  const loadCartData = async (token) => {
    try {
      const response = await axios.get("http://localhost:8080/api/cart", {
        headers: { Authorization: `Bearer ${token}` },
      });

      // Assuming response.data.items is an object like { foodId: quantity }
      setQuantities(response.data.items || {});
    } catch (error) {
      console.error("Error loading cart data:", error);
    }
  };

  // On component mount
  useEffect(() => {
    const init = async () => {
      await loadFoodList();

      const savedToken = localStorage.getItem("token");
      if (savedToken) {
        setToken(savedToken);
        await loadCartData(savedToken);
      }
    };

    init();
  }, []);

  // Increase quantity in both frontend and backend
  const increaseQty = async (foodId) => {
    setQuantities((prev) => ({
      ...prev,
      [foodId]: (prev[foodId] || 0) + 1,
    }));

    if (!token) return;

    try {
      await axios.post(
        "http://localhost:8080/api/cart",
        { foodId },
        { headers: { Authorization: `Bearer ${token}` } }
      );
    } catch (error) {
      console.error("Error adding to cart:", error);
    }
  };

  // Decrease quantity
  const decreaseQty = async (foodId) => {
    setQuantities((prev) => ({
      ...prev,
      [foodId]: prev[foodId] > 0 ? prev[foodId] - 1 : 0,
    }));

    if (!token) return;

    try {
      await axios.put(
        "http://localhost:8080/api/cart/remove",
        { foodId },
        { headers: { Authorization: `Bearer ${token}` } }
      );
    } catch (error) {
      console.error("Error removing from cart:", error);
    }
  };

  // Clear all items for one product
  const clearAll = (foodId) =>
    setQuantities((prev) => ({ ...prev, [foodId]: 0 }));

  // Remove a foodId completely from cart
  const removeFromCart = (foodId) => {
    setQuantities((prev) => {
      const updated = { ...prev };
      delete updated[foodId];
      return updated;
    });
  };

  const contextValue = {
    foodList,
    quantities,
    setQuantities,
    increaseQty,
    decreaseQty,
    clearAll,
    removeFromCart,
    token,
    setToken,
    loadCartData,
  };

  return (
    <StoreContext.Provider value={contextValue}>
      {children}
    </StoreContext.Provider>
  );
};
