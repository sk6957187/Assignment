import React, { useRef } from 'react';
import { categories } from '../../assets/assets';
import './ExploreMenu.css';

const ExploreMenu = ({ category, setCategory }) => {
  const menuRef = useRef(null);

  const scrollLeft = () => {
    menuRef.current?.scrollBy({ left: -200, behavior: 'smooth' });
  };

  const scrollRight = () => {
    menuRef.current?.scrollBy({ left: 200, behavior: 'smooth' });
  };

  return (
    <div className="explore-menu-section py-5">
      <div className="container">
        <div className="d-flex justify-content-between align-items-center mb-2">
          <h2 className="m-0 fw-bold">Explore Our Menu</h2>
          <div className="d-flex gap-3">
            <i className="bi bi-arrow-left-circle scroll-icon" onClick={scrollLeft}></i>
            <i className="bi bi-arrow-right-circle scroll-icon" onClick={scrollRight}></i>
          </div>
        </div>

        <p className="text-secondary mb-4">
          Discover a wide variety of delicious dishes from different cuisines.
        </p>

        <div ref={menuRef} className="d-flex gap-4 overflow-auto explore-menu-list">
          {categories.map((item, index) => (
            <div
              key={index}
              className="text-center explore-menu-item"
              onClick={() =>
                setCategory(prev => (prev === item.name.toLowerCase() ? 'All' : item.name.toLowerCase()))
              }
            >
              <img
                src={item.image}
                className={`rounded-circle ${category === item.name.toLowerCase() ? 'active' : ''}`}
                alt={item.name}
                height="100"
                width="100"
              />
              <p className="mt-2 mb-0">{item.name}</p>
            </div>
          ))}
        </div>
      </div>
      <hr />
    </div>
  );
};

export default ExploreMenu;
