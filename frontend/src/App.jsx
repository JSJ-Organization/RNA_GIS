import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomeView from './views/HomeView';
import InputView from "./views/InputView";

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<HomeView />} />
        <Route path='/coordinate' element={<InputView />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App
