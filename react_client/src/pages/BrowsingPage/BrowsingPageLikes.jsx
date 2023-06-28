import React, { useState, useEffect } from 'react';
import { Grid, Typography } from '@material-ui/core';

import GridLayout from '../../layouts/GridLayout/GridLayout';
import SongCard from '../../components/Cards/SongCard';
import api from '../../api/api';
import useStyles from './styles';

const BrowsingPageLikes = () => {
  const classes = useStyles();

  const [songsData, setSongsData] = useState([]);

  useEffect(() => {
    const getSongs = async () => {
      const { data } = await api.get('/likes/songs');
      //TODO error handler
      setSongsData(data);
    };

    getSongs();
  }, []);

  return (
    <>
      <GridLayout>
        <Grid item xs={12}>
          <Typography className={classes.heading}>Liked Songs</Typography>
        </Grid>
        {songsData.length > 0 ? (
          songsData.map((data, index) => {
            return (
              <Grid key={data.id} item xs={12} md={6} lg={4}>
                <SongCard
                  data={data}
                  backgroundColor={'#8D99AE'}
                />
              </Grid>
            );
          })
        ) : (
          <Typography color="primary" variant="h6">
            No Liked Songs
          </Typography>
        )}
      </GridLayout>
    </>
  );
};

export default BrowsingPageLikes;
