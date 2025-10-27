import { useState } from 'react';
import { View, Text, Pressable, Platform } from 'react-native';
import DateTimePicker from '@react-native-community/datetimepicker';
import Icon from 'react-native-vector-icons/Ionicons';
import { styles } from './style';


interface DatePickerFieldProps {
  label: string;
  placeholder: string;
  value: string;
  onChangeText: (date: string) => void;
}


export function DatePickerField({
  label,
  placeholder,
  value,
  onChangeText
}: DatePickerFieldProps) {
  const [showPicker, setShowPicker] = useState(false);
  const [date, setDate] = useState(value ? new Date(value) : new Date());

  const handleDateChange = (event: any, selectedDate?: Date) => {
    setShowPicker(Platform.OS === 'ios'); // No iOS mantém aberto, no Android fecha
    
    if (selectedDate) {
      setDate(selectedDate);
      // Formata para YYYY-MM-DD
      const formattedDate = selectedDate.toISOString().split('T')[0];
      onChangeText(formattedDate);
    }
  };

  const showDatePicker = () => {
    setShowPicker(true);
  };

  const formatDisplayDate = (dateString: string) => {
    if (!dateString) return '';
    const [year, month, day] = dateString.split('-');
    return `${day}/${month}/${year}`;
  };

  return (
    <View style={styles.inputContainer}>
      <Text style={styles.label}>{label}</Text>
      
      <Pressable 
        style={styles.comboboxWrapper}
        onPress={showDatePicker}
      >
        <Text style={[
          styles.input, 
          !value && { color: '#858587' }
        ]}>
          {value ? formatDisplayDate(value) : placeholder}
        </Text>
        <Icon 
          name="calendar-clear-outline" 
          size={25} 
          color="#136F6C" 
          style={styles.iconRight} 
        />
      </Pressable>

      {showPicker && (
        <DateTimePicker
          value={date}
          mode="date"
          display={Platform.OS === 'ios' ? 'spinner' : 'default'}
          onChange={handleDateChange}
        />
      )}
      
      <View style={styles.underline} />
    </View>
  );
}