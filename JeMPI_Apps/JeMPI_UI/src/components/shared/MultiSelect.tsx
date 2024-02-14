/* eslint-disable react-hooks/exhaustive-deps */
import { Theme, useTheme } from '@mui/material/styles'
import OutlinedInput from '@mui/material/OutlinedInput'
import InputLabel from '@mui/material/InputLabel'
import MenuItem from '@mui/material/MenuItem'
import FormControl from '@mui/material/FormControl'
import Select, { SelectChangeEvent } from '@mui/material/Select'
import Chip from '@mui/material/Chip'
import { useEffect, useState } from 'react'
import { Box } from '@mui/material'
import { Dispatch, SetStateAction } from 'react'

const ITEM_HEIGHT = 48
const ITEM_PADDING_TOP = 8
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 300
    }
  }
}

function getStyles(value: string, personName: readonly string[], theme: Theme) {
  return {
    fontWeight:
      personName.indexOf(value) === -1
        ? theme.typography.fontWeightRegular
        : theme.typography.fontWeightMedium
  }
}

type MultiSelectProps<T extends string> = {
  listValues: T[]
  label: string
  defaultSelectedValues: T[]
  setSelectedValues: Dispatch<SetStateAction<T[]>>
}
const MultiSelect = <T extends string>({
  listValues,
  label,
  defaultSelectedValues,
  setSelectedValues
}: MultiSelectProps<T>) => {
  const theme = useTheme()
  const [selectedValuesList, setSelectedValuesList] = useState(
    defaultSelectedValues
  )
  const handleChange = (
    event: SelectChangeEvent<typeof selectedValuesList>
  ) => {
    const {
      target: { value }
    } = event
    setSelectedValuesList(value as T[])
  }
  useEffect(() => {
    setSelectedValues(selectedValuesList)
  }, [selectedValuesList])
  return (
    <FormControl>
      <InputLabel id="multiple-status-label">{label}</InputLabel>
      <Select
        sx={{ minWidth: 300, maxHeight: 55 }}
        labelId="multiple-status-label"
        id="multiple-chip"
        multiple
        value={selectedValuesList}
        onChange={handleChange}
        input={<OutlinedInput label={label} />}
        renderValue={selected => (
          <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
            {selected.map(value => (
              <Chip key={value} label={value} />
            ))}
          </Box>
        )}
        MenuProps={MenuProps}
      >
        {listValues.map(value => (
          <MenuItem
            key={value}
            value={value}
            style={getStyles(value, selectedValuesList, theme)}
          >
            {value}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  )
}

export default MultiSelect
