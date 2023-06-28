import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';

import useStyles from './styles';
import Logo from '../Logo/Logo';

const TopBar = () => {
  const classes = useStyles();

  return (
    <AppBar className={classes.topBar}>
      <Toolbar>
        <Logo />
      </Toolbar>
    </AppBar>
  );
};

export default TopBar;
