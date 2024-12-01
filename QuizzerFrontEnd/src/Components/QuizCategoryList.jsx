import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import { Box } from "@mui/material";
import { getQuizzesByCategory } from "../services/api";


function QuizzesByCategory() {
  // States
  const [quizList, setQuizList] = useState([]);
  const location = useLocation(); 
  const category = location.state?.quizCategory;
  
  useEffect(() => {
    if (category?.id) {
      fetchQuizzes(category.id);
    }
  }, [category]);

  const fetchQuizzes = (categoryId) => {
    getQuizzesByCategory(categoryId)
      .then((responseData) => {
        if (responseData.error) {
          console.error("Error fetching quizzes:", responseData.error);
          setQuizList([]);
        } else {
          console.log("Fetched quizzes:", responseData);
          setQuizList(responseData);
        }
      })
      .catch((err) => console.error("Error fetching quizzes:", err));
  };
  

  return (
    <Box sx={{ width: "100%", marginTop: 8 }}>
      <Typography
        variant="h4"
        gutterBottom
        sx={{ textAlign: "left", marginBottom: 2 }}
      >
        Quizzes in {category?.name || "Category"}
      </Typography>

      {quizList && quizList.length > 0 ? (
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
                <TableCell sx={{ fontWeight: "bold" }} align="left">
                  Added On
                </TableCell>
                <TableCell sx={{ fontWeight: "bold" }} align="left">
                  See results
                </TableCell>
              </TableRow>
            </TableHead>

            <TableBody>
              {quizList.map((quiz) => (
                <TableRow
                  key={quiz.id}
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <TableCell
									component="th"
									scope="row"
									style={{ cursor: "pointer", color: "#1976d2" }}
									onClick={() => handleQuizNameClick(quiz)}
								>
									{quiz.name}
                  </TableCell>
                  <TableCell align="left">{quiz.description}</TableCell>
                  <TableCell align="left">{quiz.addedOn}</TableCell>
                  <TableCell
									component="th"
									scope="row"
									style={{ cursor: "pointer", color: "#1976d2" }}
									onClick={() => handleQuizResultsClick(quiz)}
									align="left"
								>
									See results
								</TableCell>                
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      ) : (
        <Typography variant="h6" sx={{ textAlign: "center" }}>
          No quizzes found for this category
        </Typography>
      )}
    </Box>
  );
}

export default QuizzesByCategory;
