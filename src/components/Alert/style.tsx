import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
  overlay: {
    flex: 1,
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },

  alertContainer: {
    backgroundColor: 'white',
    borderRadius: 12,
    width: '100%',
    maxWidth: 320,
    shadowColor: '#000',
    shadowOffset: {width: 0,height: 2},
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
    elevation: 5,
  },

  header: {
    padding: 20,
    paddingBottom: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#f0f0f0',
  },

  title: {
    fontSize: 18,
    fontWeight: 'bold',
    textAlign: 'center',
    color: '#009490',
  },

  body: {
    padding: 20,
    paddingVertical: 15,
  },

  message: {
    fontSize: 16,
    textAlign: 'center',
    lineHeight: 22,
    color: '#333',
  },

  footer: {
    padding: 20,
    paddingTop: 10,
  },

  confirmButton: {
    backgroundColor: '#009490',
    paddingVertical: 12,
    paddingHorizontal: 24,
    borderRadius: 8,
    alignItems: 'center',
  },

  confirmButtonText: {
    color: 'white',
    fontSize: 16,
    fontWeight: '600',
  },
  
});