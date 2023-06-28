import React, { useState, useEffect } from 'react';
import { Grid, Typography } from '@material-ui/core';

import GridLayout from '../../layouts/GridLayout/GridLayout';
import AlbumCard from '../../components/Cards/AlbumCard';
import api from '../../api/api';
import useStyles from './styles';

const BrowsingPageAlbums = () => {
  const classes = useStyles();

  const [albumsData, setAlbumsData] = useState([]);

  useEffect(() => {
    const getAlbums = async () => {
      const { data } = await api.get('/albums');
      //TODO error handler
      setAlbumsData(data);
    };

    getAlbums();
  }, []);

  return (
    <>
      <GridLayout>
        <Grid item xs={12}>
          <Typography className={classes.heading}>Albums</Typography>
        </Grid>
        {albumsData.map((data, index) => {
          return (
            <Grid key={data.id} item xs={12} md={6} lg={4}>
              <AlbumCard
                data={data}
                backgroundColor={'#8D99AE'}
              />
            </Grid>
          );
        })}
      </GridLayout>
    </>
  );
};

export default BrowsingPageAlbums;
