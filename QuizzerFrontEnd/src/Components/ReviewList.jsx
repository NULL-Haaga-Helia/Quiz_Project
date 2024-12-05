import { useEffect } from "react";



function ReviewsList(){

    const[ReviewsList, setReviewsList] = useState([]);
    const navigate = useNavigate();


    useEffect(() => {
        fetchReviews();
    }, []);

    const fetchReviews = () => {
        getAllReviews()
        .then((responseData) => {
            console.log("Fetched reviews:", responseData);
            setReviewsList(responseData);
        })
        .catch((err) => console.error("Error fetching reviews", err));
    };

    return (
        <Box sx={{ width: "100%", marginTop: 8 }}>
          <Typography
            variant="h4"
            gutterBottom
            sx={{ textAlign: "left", marginBottom: 2 }}
          >
            Reviews
          </Typography>
    
          {ReviewsList && ReviewsList.length > 0 ? (
            <TableContainer component={Paper}>
              <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                  <TableRow>
                    <TableCell sx={{ fontWeight: "bold" }} align="left">
                      Nickname
                    </TableCell>
                    <TableCell sx={{ fontWeight: "bold" }} align="left">
                      rating
                    </TableCell>
                    <TableCell sx={{ fontWeight: "bold" }} align="left">
                     review
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
              No Reviews found
            </Typography>
          )}
        </Box>
      );
}