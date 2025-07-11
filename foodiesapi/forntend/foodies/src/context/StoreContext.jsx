import { createContext, useEffect, useState } from "react";
import { fetchFoodList as fetchFoodListAPI } from "../service/FoodServices"; // ✅ alias the import

export const StoreContext = createContext(null);

export const StoreProvider = ({ children }) => {
    const [foodList, setFoodList] = useState([]);

    const loadFoodList = async () => {
        try {
            const response = await fetchFoodListAPI(); // ✅ correct function
            setFoodList(response);
            console.log("Food list loaded:", response);
        } catch (error) {
            console.error("Error fetching food list:", error);
        }
    };

    useEffect(() => {
        loadFoodList();
    }, []);

    const contextValue = {
        foodList,
    };

    return (
        <StoreContext.Provider value={contextValue}>
            {children}
        </StoreContext.Provider>
    );
};
