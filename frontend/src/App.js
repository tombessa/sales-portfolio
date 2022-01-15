import React, { useState, useEffect, useCallback, useMemo, createContext } from "react";
import { handleApiErrors } from './utils';
import ScrollToTop from './utils/ScrollToTop';
//@material-ui
import makeStyles from "@material-ui/core/styles/makeStyles";
import CircularProgress from '@material-ui/core/CircularProgress';
import Typography from '@material-ui/core/Typography';
import Fade from '@material-ui/core/Fade';
import Grid from '@material-ui/core/Grid';
import MuiAlert from '@material-ui/lab/Alert';
import Backdrop from '@material-ui/core/Backdrop';
import Button from '@material-ui/core/Button';

//Layout components
import Header from './componentes/layout/Header/Header';
import Footer from './componentes/layout/Footer/Footer';

//Rotes components
import { BrowserRouter as Router, Route, Routes  as Switch } from "react-router-dom";
import HomePage from './views/HomePage';
//App context
export const AppDataContext = createContext([{}, () => { }]);

export default function App() {
  const classes = useStyles();
  const [isLoading, setIsLoading] = useState(false);
  const [notify, setNotify] = useState({ message: undefined, type: undefined });
  const [userIsLoading, setUserIsLoading] = useState(true);
  const [cleanScreen, setCleanScreen] = useState(undefined);
  useEffect(() => {

  }, []);


  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setNotify({ message: undefined, type: undefined });
  };

  useEffect(
      () => {
        const timer = setTimeout(() => {
          setNotify({ message: undefined, type: undefined });
        }, 6000);
        return () => {
          clearTimeout(timer);
        };
      },
      [notify],
  );

  const rotas = useMemo(() => (
      <div className={classes.container}>
        <Switch>
          <Route path="/" exact component={() => <HomePage />} />
        </Switch>
      </div>
  ), [classes]);
  const logout = () => {
    console.log('logout');
  }

  return (
      <div className={classes.container}>
        <div className="notranslate">
          <Fade
              in={userIsLoading}
              style={{
                transitionDelay: userIsLoading ? '800ms' : '0ms',
              }}
              unmountOnExit
          >
            <div className={classes.message}>
              <Grid
                  container
                  direction="column"
                  justifyContent="center"
                  alignItems="center"
              >
                <Typography variant="h5" component="h1" gutterBottom>Loading user permissions</Typography>
                <CircularProgress className={classes.placeholder} />
              </Grid>
            </div>
          </Fade>
          <Backdrop className={classes.backdrop} open={isLoading} >
            <CircularProgress color="inherit" />
          </Backdrop>
          <AppDataContext.Provider value={setCleanScreen, logout}>
            <div className={classes.root}>
              <Router>
                <ScrollToTop />
                <Header  />
                {notify.message && <MuiAlert elevation={6} variant="filled" onClose={handleClose} severity={notify.type || "info"}>{notify.message}</MuiAlert>}
                {rotas}
              </Router>
              <Footer />
            </div>}
          </AppDataContext.Provider>
          <Typography variant="h5" component="h1" gutterBottom className={classes.message} align="center" hidden={userIsLoading}>

            <br/>
            <Button onClick={()=>{console.log("doSomething!");}}>Click here to make a login again</Button>
          </Typography>
        </div>
      </div>
  );

}


const useStyles = makeStyles(theme => ({
  root: {
    height: "100%",
  },
  container: {
    margin: theme.spacing(2)
  },
  message: {
    margin: theme.spacing(10),
  },
  backdrop: {
    zIndex: theme.zIndex.drawer + 999,
    color: '#fff',
  },
}));