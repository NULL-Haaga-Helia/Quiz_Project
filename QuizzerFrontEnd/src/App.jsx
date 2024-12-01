//import { useState, useEffect } from "react";
//import reactLogo from "./assets/react.svg";
//import viteLogo from "/vite.svg";
//import "./App.css";
import { Outlet } from "react-router-dom";
import Header from "./Components/Header";

function App() {
	return (
		<>
			<Header />
			<Outlet />
		</>
	);
}

export default App;
