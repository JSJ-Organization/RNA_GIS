import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomeView from './views/HomeView';
import InputView from "./views/InputView";
import MapView from "./views/MapView";

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<HomeView />} />
        <Route path='/coordinate' element={<InputView />} />
        <Route path='/coordinate/map' element={<MapView />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App
