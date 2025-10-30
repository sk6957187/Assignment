import { createContext, useEffect, useState } from "react";
import { fetchFoodList as fetchFoodListAPI } from "../service/FoodServices";
import axios from "axios";
import { addToCart, getCartData, removeQtyFromCart } from "../service/cartService";

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

   const items =  await getCartData(token);
    setQuantities(items);

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

    await addToCart(foodId, token);
  };

  // Decrease quantity
  const decreaseQty = async (foodId) => {
    setQuantities((prev) => ({
      ...prev,
      [foodId]: prev[foodId] > 0 ? prev[foodId] - 1 : 0,
    }));
    await removeQtyFromCart(foodId, token);


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
