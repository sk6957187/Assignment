import { createContext, useEffect, useState } from "react";
import { fetchFoodList as fetchFoodListAPI } from "../service/FoodServices"; // ✅ alias the import

export const StoreContext = createContext(null);

export const StoreProvider = ({ children }) => {
    const [foodList, setFoodList] = useState([]);
    const [quantities, setQuantities] = useState({});

    const loadFoodList = async () => {
        try {
            const response = await fetchFoodListAPI();
            setFoodList(response);
            // console.log("Food list loaded:", response);
        } catch (error) {
            console.error("Error fetching food list:", error);
        }
    };

    useEffect(() => {
        loadFoodList();
    }, []);

    const increaseQty= (foodId) =>{
        setQuantities((prev) => ({
            ...prev,
            [foodId]: (prev[foodId] || 0) + 1}));
    };

    const clearAll = (foodId) =>{
        setQuantities((prev) => ({...prev, [foodId] : 0}))
    };

    const decreaseQty = (foodId) => {
        setQuantities((prev) => ({...prev, [foodId]: prev[foodId] > 0 ? prev[foodId] - 1 : 0}));
    };

    const contextValue = {
        foodList,
        increaseQty,
        decreaseQty,
        quantities,
        clearAll
    };
    
    return (
        <StoreContext.Provider value={contextValue}>
            {children}
        </StoreContext.Provider>
    );
};
