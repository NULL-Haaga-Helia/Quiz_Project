import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import { Box } from "@mui/material";
import { getAllCategories } from "../services/api";



function CategoryList() {
  // States
  const [categoryList, setCategoryList] = useState([]);
  const navigate = useNavigate();

  // Function
  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = () => {
    getAllCategories()
      .then((responseData) => {
        console.log("Fetched categories:", responseData);
        setCategoryList(responseData);
      })
      .catch((err) => console.error("Error fetching categories:", err));
  };

  const handleQuizCategoryClick = (quizCategory) => {
    console.log("Navigate with category:", quizCategory);
    navigate("/quizcategorylist", { state: { quizCategory } });
  };



  return (
    <Box sx={{ width: "100%", marginTop: 8 }}>
      <Typography
        variant="h4"
        gutterBottom
        sx={{ textAlign: "left", marginBottom: 2 }}
      >
        Categories
      </Typography>

      {categoryList && categoryList.length > 0 ? (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell sx={{ fontWeight: "bold" }} align="left">
                  Name
                </TableCell>
                <TableCell sx={{ fontWeight: "bold" }} align="left">
                  Description
                </TableCell>
              </TableRow>
            </TableHead>

            <TableBody>
              {categoryList.map((quizCategory) => (
                <TableRow
                  key={quizCategory.id}
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <TableCell
                    component="th"
                    scope="row"
                    style={{ cursor: "pointer", color: "#1976d2" }}
                    onClick={() => handleQuizCategoryClick(quizCategory)}
                  >
                    {quizCategory.name}
                  </TableCell>
                  <TableCell align="left">{quizCategory.description}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : (
        <Typography variant="h6" sx={{ textAlign: "center" }}>
          No categories found
        </Typography>
      )}
    </Box>
  );
}
export default CategoryList;