import { FONTS } from "@/src/constants/fonts";
import { StyleSheet } from "react-native"

export const styles = StyleSheet.create({

    scrollContainer: {
        flexGrow: 1,
        paddingBottom: 20,
    },
    
    header: {
        justifyContent:"center",
        height:150,
        backgroundColor: '#009490',
        paddingHorizontal: 20,
        paddingVertical: 25,
        
    },

    titleHeader: {
        alignItems: "center",
        marginBottom: 10,
    },

    titleText: {
        fontFamily: FONTS.montserrat.bold,
        fontSize: 22,
        color: '#FFFFFF',
        textAlign:"center",
    },

    containerCharts:{
        margin:20,
    },
    
});