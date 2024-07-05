// PathContext.js
import { createContext, useContext } from 'react';
import PropTypes from 'prop-types';

const PathContext = createContext();

export const PathProvider = ({ children, value }) => {
  return (
    <PathContext.Provider value={value}>
      {children}
    </PathContext.Provider>
  );
};

PathProvider.propTypes = {
    children: PropTypes.node.isRequired,
    value: PropTypes.object.isRequired,
  };

export const usePath = () => useContext(PathContext);
