import React, { useState, useContext, useCallback } from 'react';
import { Link } from 'react-router-dom';
import clsx from 'clsx';
import { AppDataContext } from "../../../App";
import { handleApiErrors } from '../../../utils';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import AccountCircle from '@material-ui/icons/AccountCircle';
import GetAppIcon from '@material-ui/icons/GetApp';
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';
import Hidden from "@material-ui/core/Hidden";
import Drawer from "@material-ui/core/Drawer";
import IconButton from "@material-ui/core/IconButton";
import List from "@material-ui/core/List";
import ListItemText from "@material-ui/core/ListItemText";
import ListItem from "@material-ui/core/ListItem";
import Divider from "@material-ui/core/Divider";

export default function Header({ ...props }) {
  const { setCleanScreen, logout} = props;
  const appData = useContext(AppDataContext);
  const setIsLoading = appData.setIsLoading;
  const setNotify = appData.setNotify;
  const classes = useStyles();
  const [anchorEl, setAnchorEl] = useState(null);
  const [open, setOpen] = useState(false);

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  const handleMenu = event => {
    setAnchorEl(event.currentTarget);
  };

  const appSetForcarLimpaTela = () => {
    appData.setCleanScreen(Math.random());
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <>
      <AppBar
        position="fixed"
        className={clsx(classes.appBar, {
          [classes.appBarShift]: open,
        })}
      >
        <Toolbar>
          <Hidden smDown implementation="css">
            {menuItens.map((item, key) =>
              <Button
                color="inherit"
                className={classes.button}
                key={key}
                component={Link} to={item.link} onClick={appSetForcarLimpaTela}
              >
                {item.text}
              </Button>
            )}
          </Hidden>
          <Hidden mdUp implementation="css">
            <IconButton
              color="inherit"
              aria-label="open drawer"
              onClick={handleDrawerOpen}
              edge="start"
              className={clsx(classes.menuButton, open && classes.hide)}
            >
              <MenuIcon />
            </IconButton>
            <Drawer
              className={classes.drawer}
              variant="persistent"
              anchor="left"
              open={open}
              classes={{
                paper: classes.drawerPaper,
              }}
            >
              <div className={classes.drawerHeader}>
                <IconButton onClick={handleDrawerClose}>
                  <ChevronLeftIcon />
                </IconButton>
              </div>
              <Divider />
              <List>
                {menuItens.map((item, index) => (
                  <ListItem key={index} component={Link} to={item.link} >
                    <ListItemText primary={item.texto} />
                  </ListItem>
                ))}
              </List>
            </Drawer>
          </Hidden>
          <Button
            color="inherit"
            className={classes.button}
            onClick={handleMenu}
            endIcon={<AccountCircle />}
          >
            USER_NAME
            <br />
            USER_PROFILE
          </Button>
          <Menu
            id="menu-appbar"
            anchorEl={anchorEl}
            anchorOrigin={{
              vertical: 'top',
              horizontal: 'right',
            }}
            keepMounted
            transformOrigin={{
              vertical: 'top',
              horizontal: 'right',
            }}
            open={Boolean(anchorEl)}
            onClose={handleClose}
          >
            <MenuItem onClick={() => { logout(); handleClose() }}>Log out</MenuItem>
          </Menu>

        </Toolbar>
      </AppBar>
      <Toolbar />
    </>
  );
}

const menuItens = [
  { text: "HomePage", link: "/" },
];

const drawerWidth = 240;

const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
  },
  appBar: {
    transition: theme.transitions.create(['margin', 'width'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  hide: {
    display: 'none',
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: drawerWidth,
  },
  title: {
    flexGrow: 1,
  },
  toolbar: {
    minHeight: 56,
    [theme.breakpoints.up("xs")]: {
      minHeight: 48,
    },
    [theme.breakpoints.up('sm')]: {
      minHeight: 64,
    },
  },

}));