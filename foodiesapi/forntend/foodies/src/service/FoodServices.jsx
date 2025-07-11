import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

export const fetchFoodList = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/foods`);
    return response.data;
  } catch (error) {
    console.error('Error fetching food list:', error);
    throw error;
  }
}