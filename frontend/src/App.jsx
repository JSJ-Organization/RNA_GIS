import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomeView from './views/HomeView';
import BasicLayout from "./layout/BasicLayout";
import CoordInput from "./views/coord/CoordInput";
import CoordMap from "./views/coord/CoordMap";
import AgriInput from "./views/agri/AgriInput";
import AgriMap from "./views/agri/AgriMap";
import { HelmetProvider } from 'react-helmet-async';

function App() {

  return (
    <HelmetProvider>
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<HomeView />} />
          <Route path='/coordinate' element={<BasicLayout />} >
            <Route index element={<CoordInput />} />
            <Route path='map' element={<CoordMap />} />
          </Route>
          <Route path='/agricultural' element={<BasicLayout />} >
            <Route index element={<AgriInput />} />
            <Route path='map' element={<AgriMap />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </HelmetProvider>
  );
}

export default App
