import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({

footerContainer: {
    position: "relative",
  },
  
  footer: {
    flexDirection: "row",
    backgroundColor: "#FFFFFF",
    borderTopWidth: 1,
    borderTopColor: "#E0E0E0",
    paddingHorizontal: 10,
    paddingTop: 8,
    paddingBottom: 8,
    height: 70,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: -2 },
    shadowOpacity: 0.1,
    shadowRadius: 3,
    elevation: 5,
    justifyContent: "space-between",
    alignItems: "center",
  },

  topCurve: {
    backgroundColor:'#FFFFFF',
    position: "absolute",
    top: -50,
    left: '50%',
    marginLeft: -40,
    width: 100,
    height: 51,
    borderTopWidth: 1,
    borderTopLeftRadius: 50,
    borderTopRightRadius: 50,
    borderTopColor: '#E0E0E0', 
    borderLeftWidth: 1,
    borderLeftColor: '#E0E0E0',
    borderRightWidth: 1,
    borderRightColor: '#E0E0E0',
  },

  tab: {
    alignItems: "center",
    justifyContent: "center",
    paddingVertical: 8,
    paddingHorizontal: 12,
    borderRadius: 12,
    flex: 1,
    marginHorizontal: 2,
  },

  centerTab: {
    position: "absolute",
    top: -40,
    left: '50%',
    marginLeft: -20,
    alignItems: "center",
    justifyContent: "center",
  },

  activeTab: {
    backgroundColor: '#00948f27',
  },

  icon: {
    width: 32,
    height: 32,
    tintColor: '#666666',
  },

  centerIcon: {
    tintColor: '#136F6C',
    width: 35,
    height: 35,
  },

  activeIcon: {
    tintColor: '#009490',
  },
  
});