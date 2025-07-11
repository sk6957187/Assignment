import axios from "axios";

const API_URL = "http://localhost:8080/api/foods";

export const addFood = async (foodData, image) => {
  const formData = new FormData();

  formData.append("food", JSON.stringify(foodData));
  formData.append("file", image);

  try {
    const response = await axios.post(API_URL, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return response;
  } catch (error) {
    console.log(error.response || error.message);
    throw error;
  }
};

export const updateFood = async (foodId, formData) => {
  try {
    const response = await axios.put(API_URL + "/" + foodId, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return response;
  } catch (error) {
    console.log(error.response || error.message);
    throw error;
  }
};

export const getFoodList = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data;
  } catch (error) {
    console.error("Error fetching food list:", error);
    throw error;
  }
};

export const removeFood = async (foodId) => {
  try {
    const response = await axios.delete(API_URL + "/" + foodId);
    return response.status === 204 ? true : false;
  } catch (error) {
    console.error("Error removing food item:", error);
    throw error;
  }
};