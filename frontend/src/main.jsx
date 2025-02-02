import ReactDOM from 'react-dom/client';
import App from './App.jsx';
import "./assets/scss/style.scss";
import { HelmetProvider } from 'react-helmet-async';

ReactDOM.createRoot(document.getElementById('root')).render(
  <HelmetProvider>
    {/* <React.StrictMode> */}
      <App />
    {/* </React.StrictMode> */}
  </HelmetProvider>
)
