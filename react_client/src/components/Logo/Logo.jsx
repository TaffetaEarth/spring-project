import React from 'react';

import Typography from '@material-ui/core/Typography';

import useStyles from './styles';

const Logo = () => {
  const classes = useStyles();

  return (
    <>
      <Typography className={classes.logoText}>CoolestMusicBiblio</Typography>
    </>
  );
};

export default Logo;
