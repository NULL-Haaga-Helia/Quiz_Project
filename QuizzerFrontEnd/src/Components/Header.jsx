import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import { Link } from "react-router-dom";
import { Box } from "@mui/material";


function Header() {
	return (
		<AppBar>
			<Toolbar>
				<Typography variant="h6" sx={{ marginRight: 4 }}>
					Quizzer
				</Typography>

				<Box sx={{ display: "flex", gap: 2 }}>

					<Link to="/" style={{ color: "white", textDecoration: "none" }}>
						Quizzes
					</Link>

					<Link to="/CategoryList" style={{ color: "white", textDecoration: "none" }}>
						Categories
					</Link>

				</Box>
			</Toolbar>
		</AppBar>
	);
}

export default Header;
