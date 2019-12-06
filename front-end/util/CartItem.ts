export type CartItem = {
    id: number,
    name: string,
    quantity: number
}

export const addToCart = (item: CartItem) => {
    let cart: CartItem[] = getCart();
    let count = 0;
    cart = cart.filter(c => {
        if (c.id == item.id) {
            count = c.quantity;
            return false;
        }
        return true;
    });
    cart.push({id: item.id, name: item.name, quantity: count + item.quantity});
    saveCart(cart);
};

export const getCart = () => {
    let cart: CartItem[] = [];
    const cartStr = sessionStorage.getItem('cart');
    if (cartStr != null) {
        try {
            cart = JSON.parse(cartStr);
        } catch (e) {

        }
    }
    return cart;
};

export const saveCart = (cart: CartItem[]) => {
    sessionStorage.setItem('cart', JSON.stringify(cart));
}