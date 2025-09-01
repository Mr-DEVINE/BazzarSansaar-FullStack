import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

function App() {
  return (
    <Router>
      {/* Navbar will go here */}
      <main className="container">
        <Routes>
          {/* Our page routes will go here */}
          <Route path="/" element={<h1>Welcome to BazzarSansaar</h1>} />
        </Routes>
      </main>
    </Router>
  );
}

export default App;
