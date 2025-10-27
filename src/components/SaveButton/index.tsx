import { Pressable, Text, ActivityIndicator } from 'react-native';
import { styles } from './style';

interface SaveButtonProps {
  onPress: () => void;
  loading?: boolean;
  disabled?: boolean;
  title?: string;
}

export function SaveButton({ 
  onPress, 
  loading = false, 
  disabled = false,
  title = "Salvar Transação" 
}: SaveButtonProps) {
  return (
    <Pressable 
      style={[
        styles.saveButton, 
        (loading || disabled) && styles.saveButtonDisabled
      ]}
      onPress={onPress}
      disabled={loading || disabled}
    >
      {loading ? (
        <ActivityIndicator color="#FFFFFF" />
      ) : (
        <Text style={styles.saveButtonText}>{title}</Text>
      )}
    </Pressable>
  );
}