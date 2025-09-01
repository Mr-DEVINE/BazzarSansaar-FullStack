import React, { createContext, useState, useEffect, useContext } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

// Create the context
export const AuthContext = createContext(null);

// Create a custom hook for easy access to the context
export const useAuth = () => {
    return useContext(AuthContext);
};

// Create the provider component
export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [cart, setCart] = useState(null);
    const navigate = useNavigate();

    // Effect to load user from local storage on initial app load
    useEffect(() => {
        const storedUser = localStorage.getItem('user');
        if (storedUser) {
            const parsedUser = JSON.parse(storedUser);
            setUser(parsedUser);
            // Set token for all subsequent axios requests
            axios.defaults.headers.common['Authorization'] = `Bearer ${parsedUser.token}`;
            fetchCart();
        }
    }, []);

    const login = async (credentials) => {
        try {
            const response = await axios.post('/api/auth/login', credentials);
            if (response.data && response.data.token) {
                const userData = {
                    username: credentials.username, // Or get from response if available
                    token: response.data.token
                };
                setUser(userData);
                localStorage.setItem('user', JSON.stringify(userData));
                // Set token for all subsequent axios requests
                axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`;
                await fetchCart(); // Fetch cart immediately after login
                navigate('/'); // Redirect to home page
                return true;
            }
        } catch (error) {
            console.error("Login failed:", error);
            return false;
        }
    };

    const logout = () => {
        setUser(null);
        setCart(null);
        localStorage.removeItem('user');
        delete axios.defaults.headers.common['Authorization'];
        navigate('/login');
    };

    const fetchCart = async () => {
        try {
            const response = await axios.get('/api/cart');
            setCart(response.data);
        } catch (error) {
             if (error.response && error.response.status === 404) {
                setCart({ cartItems: [], totalPrice: 0 }); // Set an empty cart if none exists
            } else {
                console.error("Failed to fetch cart:", error);
            }
        }
    };

    const addToCart = async (productId, quantity) => {
        if (!user) {
            navigate('/login');
            return;
        }
        try {
            const response = await axios.post(`/api/cart/add?productId=${productId}&quantity=${quantity}`);
            setCart(response.data);
        } catch (error) {
            console.error("Failed to add to cart:", error);
        }
    };

    const value = { user, cart, login, logout, addToCart, fetchCart };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

