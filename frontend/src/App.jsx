import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomeView from './views/HomeView';
import BasicLayout from "./layout/BasicLayout";
import CoordInputView from "./views/input/CoordInputView";
import CoordMapView from "./views/map/CoordMapView";
import AgriInputView from "./views/input/AgriInputView";
import AgriMapView from "./views/map/AgriMapView";
import { PathProvider } from "./contexts/PathContext";
import { LoadingProvider } from "./contexts/LoadingContext";
import Loader from "./components/Loader";

function App() {

  return (
    <LoadingProvider>
      <BrowserRouter>
        <Loader />
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
            <Route index element={<CoordInputView />} />
            <Route path='map' element={<CoordMapView />} />
          </Route>
          <Route path='/agricultural' element={
            <PathProvider value={{ id: 'agricultural' }}>
              <BasicLayout />
            </PathProvider>
          } >
            <Route index element={<AgriInputView />} />
            <Route path='map' element={<AgriMapView />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </LoadingProvider>
  );
}

export default App
