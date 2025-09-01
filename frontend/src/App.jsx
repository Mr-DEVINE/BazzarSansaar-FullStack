import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';

function App() {
  return (
    <Router>
      <AuthProvider>
        {/* Navbar will go here */}
        <main className="container">
          <Routes>
            {/* Our page routes will go here */}
            <Route path="/" element={<h1>Welcome to BazzarSansaar</h1>} />
          </Routes>
        </main>
      </AuthProvider>
    </Router>
  );
}

export default App;
