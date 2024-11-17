import { useState } from "react";



export default function Addquestion(){

    const[open, setOpen] = useState(false);

    const[quiz, setQuiz] = useState({

        name: '',
        description: '',
        published: '',
        addedOn: '',


    });

    const handleClickOpne = () => {
        setOpen(true);
    }

    const handleClose = () => {
        setOpen(false);

    }

    const handleInputChange = (e) => {
        setQuiz({...quiz, [e.target.name]: e.target.value})
    }    

    const addQuiz = () => {
        props.saveQuiz(quiz);
        handleClose();

    }


    return(
        <div>

            <Button onClick={handleClickOpne}>
                Add Quiz
            </Button>

            <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">


            <DialogTitle id="form-dialog-title">
                New Quiz
            </DialogTitle>

                <DialogContent>

                    <Textfield 
                        autoFocus
                        margin="dense"
                        name="name"
                        value={quiz.name}
                        onChange={e => handleInputChange(e)}
                        label="Name"
                        fullWidth
                    />

                    <Textfield 
                        margin="dense"
                        name="description"
                        value={quiz.description}
                        onChange={e => handleInputChange(e)}
                        label="Description"
                        fullWidth
                    />

                    <Textfield
                        margin="dense"
                        name="published"
                        value={quiz.published}
                        onChange={e => handleInputChange(e)}
                        label ="Published"
                        fullWidth
                    />

                    <Textfield
                        margin="dense"
                        name="addedOn"
                        value={quiz.addedOn}
                        onChange={e => handleInputChange(e)}
                        label="Added on"
                        fullWidth
                    />

                </DialogContent>

            <DialogActions>
                <Button onClick={handleClose} color="primary">
                    Cancel
                </Button>

                <Button onClick={addQuiz} color="primary">
                    Save
                </Button>

            </DialogActions>
            </Dialog>

        </div>
    )
  
}