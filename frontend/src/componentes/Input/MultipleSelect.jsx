import React, { useState } from "react";
//@material-ui/core
import makeStyles from "@material-ui/core/styles/makeStyles";
import Input from '@material-ui/core/Input';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import Chip from '@material-ui/core/Chip';

export default function MultipleSelect({ ...props }) {
  const { id, options, onChange } = props
  const classes = useStyles();
  const [selectedOptions, setselectedOptions] = useState([]);

  const handleChange = event => {
    onChange(event.target.value);
    setselectedOptions(event.target.value);
  };

  const handleDelete = value => () => {
    const newOption = selectedOptions.filter(chip => chip !== value)
    setselectedOptions([...newOption]);
    onChange(newOption);
  };


  return (
    <div>
      <Select
        id={id}
        multiple
        value={selectedOptions}
        onChange={handleChange}
        input={<Input id={id} />}
        autoWidth={true}
        renderValue={selected => (
          <div className={classes.chips}>
            {selected.map(value => (
              <Chip
                key={value}
                label={value}
                className={classes.chip}
                onDelete={handleDelete(value)}
              />
            ))}
          </div>
        )}
      >
        {options.map(opt => (
          <MenuItem key={`${Math.random()}-opt${opt}`} value={opt} >
            {opt}
          </MenuItem>
        ))}
      </Select>
    </div>
  );
}

const useStyles = makeStyles(theme => ({
}));