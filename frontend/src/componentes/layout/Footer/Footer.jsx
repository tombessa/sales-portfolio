import React from "react";
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';


export default function Footer() {
  const classes = useStyles();

  return (
    <footer className={classes.footer}>
      <Grid container justifyContent="center">
        <img  src={require("../../../resources/logo.png")} className={classes.logo} alt="Logo"/>
      </Grid>
    </footer>
  );
}

const useStyles = makeStyles(theme => ({
  footer: {
    padding: theme.spacing(5),
    textAlign: "center",
    display: "flex",
    zIndex: "2",
    position: "relative",
    backgroundColor: theme.palette.primary.main,
    marginTop:"calc(5% + 800px)",
    bottom: 0,
  },
  logo: {
    height: 100,
    width: 100,
  }
}));