import React from "react";
// @material-ui/core components
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
import Input from '@material-ui/core/Input';
import MaskedInput from 'react-text-mask';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';

const defaultMaskOptions = {
  prefix: '',
  suffix: '',
  includeThousandsSeparator: true,
  thousandsSeparatorSymbol: '.',
  allowDecimal: true,
  decimalSymbol: ',',
  decimalLimit: 2,
  integerLimit: 9,
  allowNegative: false,
  allowLeadingZeroes: false,
}

function MoneyMaskCustom(props) {
	const { inputRef, mask, ...other } = props;

	const currencyMask = createNumberMask({
    ...defaultMaskOptions,
  });

	return (
		<MaskedInput
			{...other}
			ref={ref => {
				inputRef(ref ? ref.inputElement : null);
			}}
			mask={currencyMask}
		/>
	);
}


function TextMaskCustom(props) {
	const { inputRef, mask, ...other } = props;

	return (
		<MaskedInput
			{...other}
			ref={ref => {
				inputRef(ref ? ref.inputElement : null);
			}}
			mask={mask}
		/>
	);
}

export default function CustomInput({ ...props }) {

	const {
		labelText,
		id,
		error,
		fullWidth,
		onChange,
		onBlur,
		mask,
		maskMoney,
		inputProps,
		className
	} = props;

	return (
		<FormControl fullWidth={fullWidth} className={className}>
			<InputLabel error={(error !== undefined)} htmlFor={id}>{(error || labelText)}</InputLabel>
			{maskMoney &&
				<Input
					id={id}
					onChange={onChange}
					onBlur={onBlur}
					inputComponent={MoneyMaskCustom}
					inputProps={{
						mask: mask,
					}}
					error={(error !== undefined)}
					{...inputProps}
				/>}
			{mask &&
				<Input
					id={id}
					onChange={onChange}
					onBlur={onBlur}
					inputComponent={TextMaskCustom}
					inputProps={{
						mask: mask,
					}}
					error={(error !== undefined)}
					{...inputProps}
				/>}
			{!maskMoney && !mask &&
				<Input
					id={id}
					onChange={onChange}
					onBlur={onBlur}
					error={(error !== undefined)}
					{...inputProps}
				/>}
		</FormControl>
	);
}
