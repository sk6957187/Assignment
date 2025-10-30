import axios from "axios";

const API_URL = "http://localhost:8080/api/cart";

const getAuthHeaders = (token) => ({
  headers: { Authorization: `Bearer ${token}` },
});

// Add item to cart
export const addToCart = async (foodId, token) => {
  try {
    const response = await axios.post(API_URL, { foodId }, getAuthHeaders(token));
    return response.data;
  } catch (error) {
    console.error("❌ Error adding item to cart:", error.response?.data || error.message);
    throw error;
  }
};

// Remove one quantity of item from cart
export const removeQtyFromCart = async (foodId, token) => {
  try {
    const response = await axios.put(`${API_URL}/remove`, { foodId }, getAuthHeaders(token));
    return response.data;
  } catch (error) {
    console.error("❌ Error removing quantity from cart:", error.response?.data || error.message);
    throw error;
  }
};

// Fetch all cart items
export const getCartData = async (token) => {
  try {
    const response = await axios.get(API_URL, getAuthHeaders(token));
    return response.data.items || [];
  } catch (error) {
    console.error("❌ Error fetching cart data:", error.response?.data || error.message);
    throw error;
  }
};
