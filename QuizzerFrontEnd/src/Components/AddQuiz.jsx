import { useState } from "react";
import {
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
} from "@mui/material"; 
import axios from "axios"; 

export default function AddQuiz({ onQuizAdded }) {

  const [open, setOpen] = useState(false);


  const [quiz, setQuiz] = useState({
    name: "",
    description: "",
    isPublished: false,
    addedOn: new Date().toISOString().split("T")[0], 
  });

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    setQuiz({
      ...quiz,
      [name]: type === "checkbox" ? checked : value, 
    });
  };

  const saveQuiz = async () => {
    try {
      const response = await axios.post("http://localhost:8080/savequiz", quiz); 
      console.log("Quiz saved successfully:", response.data);
      onQuizAdded(response.data); 
      handleClose();
    } catch (error) {
      console.error("Error saving quiz:", error);
    }
  };

  return (
    <div>
      <Button variant="contained" color="primary" onClick={handleClickOpen}>
        Add Quiz
      </Button>

      <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
        <DialogTitle id="form-dialog-title">New Quiz</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            name="name"
            value={quiz.name}
            onChange={handleInputChange}
            label="Name"
            fullWidth
          />
          <TextField
            margin="dense"
            name="description"
            value={quiz.description}
            onChange={handleInputChange}
            label="Description"
            fullWidth
          />
          <TextField
            margin="dense"
            name="addedOn"
            value={quiz.addedOn}
            onChange={handleInputChange}
            label="Added On (YYYY-MM-DD)"
            fullWidth
          />
          <div style={{ marginTop: 10 }}>
            <label>
              <input
                type="checkbox"
                name="isPublished"
                checked={quiz.isPublished}
                onChange={handleInputChange}
              />
              Published
            </label>
          </div>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="secondary">
            Cancel
          </Button>
          <Button onClick={saveQuiz} color="primary">
            Save
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
