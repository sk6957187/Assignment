import axios from "axios";

const API_URL = "http://localhost:8080/api";

export const registerUser = async (data) => {
    try {
        var response = await axios.post(
            API_URL + "/register", data
        );
        return response;
    } catch (error) {
        throw error;
    }
}

export const login = async (data) => {
    try {
        var response = await axios.post(API_URL + "/login", data)
        return response;
    } catch (error) {
        throw error;
    }
}
