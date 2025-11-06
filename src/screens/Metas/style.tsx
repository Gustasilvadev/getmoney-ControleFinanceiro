import { FONTS } from "@/src/constants/fonts";
import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    
    scrollContainer:{
        flexGrow:1,
        paddingBottom:20,
    },

    container: {
        flex: 1,
    },

    header: {
        justifyContent: "center",
        height: 150,
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
        textAlign: "center",
    },

    subtitle:{
        fontFamily:FONTS.montserrat.bold,
        fontSize:18,
        color:'#535652',
        marginLeft:20,
        marginBottom: 10,
    },

    containerMetas:{
        marginTop:30,
    },

    cardMeta:{
        backgroundColor:'#FFFFFF',
        marginHorizontal:20,
        marginVertical:10,
        borderRadius:12,
        padding:15,
        shadowColor: '#000',
        shadowOffset: {width: 0,height: 2},
        shadowOpacity: 0.1,
        shadowRadius: 3,
        elevation: 3,
        borderLeftWidth: 0,
    },

    cardHeader: {
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "flex-start",
        marginBottom: 15,
    },

    cardTitleMeta:{
        fontFamily:FONTS.montserrat.bold,
        fontSize:16,
        color:'#535652',
        flex: 1,
        marginRight: 10,
    },

    progressContainer: {
        marginTop: 5,
    },

    progressBar: {
        height: 12,
        backgroundColor: '#E8E8E8',
        borderRadius: 6,
        marginBottom: 8,
        overflow: "hidden",
    },

    progressFill: {
        height: '100%',
        backgroundColor: '#136F6C',
        borderRadius: 6,
    },

    progressInfo:{
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "center",
    },

    cardSubTitleMeta:{
        fontFamily:FONTS.openSans.semiBold,
        fontSize: 14,
        color: '#535652',
    },

    cardSubTitlePercent: {
        fontFamily:FONTS.montserrat.bold,
        fontSize: 14,
        color: '#136F6C',
    },

    emptyText:{
        fontFamily:FONTS.montserrat.bold,
        color: '#535652',
        textAlign:"center",
        margin:30,
    },

    botao:{
        margin:30,
    },

    actionButtonsContainer: {
        flexDirection: "row",
        gap: 8,
    },
    actionButton: {
        paddingHorizontal: 12,
        paddingVertical: 6,
        borderRadius: 6,
        minWidth: 60,
        alignItems: "center",
    },
    editButton: {
        backgroundColor: '#007AFF',
    },
    deleteButton: {
        backgroundColor: '#FF3B30',
    },
    actionButtonText: {
        color: '#FFFFFF',
        fontSize: 12,
        fontFamily:FONTS.montserrat.semiBold,
    },
    
});