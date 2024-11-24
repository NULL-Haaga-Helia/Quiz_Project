import { useState } from "react";
import { Button, Dialog, DialogTitle, DialogContent, DialogActions, TextField, MenuItem } from "@mui/material";

export default function EditQuestion({ question, updateQuestion }) {
  const [open, setOpen] = useState(false);
  const [editedQuestion, setEditedQuestion] = useState({
    questionText: '',
    difficulty: '',
  });

  const handleClickOpen = () => {
    setEditedQuestion({ ...question });
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditedQuestion((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const saveQuestion = () => {
    updateQuestion(editedQuestion, question._links.self.href); // Call parent update function
    handleClose();
  };

  return (
    <div>
      <Button variant="outlined" color="primary" onClick={handleClickOpen}>
        Edit Question
      </Button>

      <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
        <DialogTitle id="form-dialog-title">Edit Question</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            name="questionText"
            value={editedQuestion.questionText}
            onChange={handleInputChange}
            label="Question Text"
            fullWidth
          />
          <TextField
            margin="dense"
            name="difficulty"
            value={editedQuestion.difficulty}
            onChange={handleInputChange}
            label="Difficulty"
            select
            fullWidth
          >
            <MenuItem value="Easy">Easy</MenuItem>
            <MenuItem value="Medium">Medium</MenuItem>
            <MenuItem value="Hard">Hard</MenuItem>
          </TextField>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="secondary">
            Cancel
          </Button>
          <Button onClick={saveQuestion} color="primary">
            Save
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
  
}
