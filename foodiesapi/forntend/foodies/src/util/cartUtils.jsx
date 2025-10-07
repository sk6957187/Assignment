export const calculateCartTotals = (cartItem, quantities) => {
    const subTotal = cartItem.reduce(
        (acc, food) => acc + food.price * quantities[food.id],
        0
    );
    const shipping = subTotal > 99 ? 0 : subTotal > 0 ? 20 : 0;
    const tax = subTotal * 0.18;
    const total = Math.ceil(subTotal + shipping + tax);

    return { subTotal, shipping, tax, total };
}