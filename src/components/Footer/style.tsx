import { FONTS } from "@/src/constants/fonts";
import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({

  container: {
    flex: 1,
    backgroundColor: "#F5F5F5",
  },

  content: {
    flex: 1,
  },

  footer: {
    flexDirection: "row",
    backgroundColor: "#FFFFFF",
    borderTopWidth: 1,
    borderTopColor: "#E0E0E0",
    paddingHorizontal: 8,
    paddingVertical: 8,
    bottom: 0,
    left: 0,
    right: 0,
    height: 85,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: -2 },
    shadowOpacity: 0.1,
    shadowRadius: 3,
    elevation: 5,
  },

  tab: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    paddingVertical: 8,
    borderRadius: 12,
  },

  centerTab: {
    transform: [{ translateY: -25 }],
    borderRadius: 40,
    marginHorizontal: 4,
    width: 60,
    height: 60, 
    alignItems: "center",
    justifyContent: "center",
  },

  activeTab: {
    backgroundColor: "#F0F8FF",
  },

  icon: {
    width: 26,
    height: 26,
    marginBottom: 4,
    tintColor: "#666666",
  },

  centerIcon: {
    tintColor: "#666666",
    width: 48,
    height: 48,
  },

  activeIcon: {
    tintColor: "#1991BD",
  },

  label: {
    fontSize: 12,
    color: "#666666",
    fontFamily:FONTS.openSans.semiBold,
    textAlign: "center",
  },

  centerLabel: {
    color: "#666666",
    fontFamily:FONTS.openSans.semiBold,
    textAlign: "center",
  },

  activeLabel: {
    color: "#1991BD",
    fontFamily:FONTS.openSans.bold,
  },

});
