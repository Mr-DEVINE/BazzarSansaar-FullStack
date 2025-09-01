import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';

const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="navbar-container">
                <Link to="/" className="navbar-brand">
                    BazzarSansaar
                </Link>
                <div className="navbar-links">
                    {/* Dynamic links will go here */}
                    <Link to="/login" className="nav-link">Login</Link>
                    <Link to="/register" className="nav-link">Register</Link>
                </div>
            </div>
        </nav>
    );
};

export default Navbar;
