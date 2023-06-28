import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
  heading: {
    color: '#4e8d7c',
    fontStyle: 'bold',
    fontSize: '4em',
  },
  button: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.primary.dark,
    color: theme.palette.primary.light,
    fontWeight: 'bold',
    minWidth: '175px',
    '&:hover': {
      color: '#000000',
      backgroundColor: theme.palette.primary.buttons,
    },
  },
}));

export default useStyles;
