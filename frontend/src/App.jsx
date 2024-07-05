import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomeView from './views/HomeView';
import BasicLayout from "./layout/BasicLayout";
import CoordInput from "./views/coord/CoordInput";
import CoordMap from "./views/coord/CoordMap";
import AgriInput from "./views/agri/AgriInput";
import AgriMap from "./views/agri/AgriMap";
import { PathProvider } from "./PathContext";

function App() {

  return (
      <BrowserRouter>
        <Routes>
          <Route path='/' element={
            <PathProvider value={{ id: 'home' }}>
              <HomeView />
            </PathProvider>
            } />
          <Route path='/coordinate' element={
            <PathProvider value={{ id: 'coordinate' }}>
              <BasicLayout />
            </PathProvider>
            } >
            <Route index element={<CoordInput />} />
            <Route path='map' element={<CoordMap />} />
          </Route>
          <Route path='/agricultural' element={
            <PathProvider value={{ id: 'agricultural' }}>
              <BasicLayout />
            </PathProvider>
          } >
            <Route index element={<AgriInput />} />
            <Route path='map' element={<AgriMap />} />
          </Route>
        </Routes>
      </BrowserRouter>
  );
}

export default App
