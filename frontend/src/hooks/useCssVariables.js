import { useEffect } from 'react';

const useCssVariables = (variables) => {
  useEffect(() => {
    Object.entries(variables).forEach(([key, value]) => {
      document.documentElement.style.setProperty(key, value);
    });
  }, [variables]);
};

export default useCssVariables;