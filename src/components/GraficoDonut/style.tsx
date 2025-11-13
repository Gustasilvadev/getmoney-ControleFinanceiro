import { FONTS } from "@/src/constants/fonts";
import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    container: {
        backgroundColor: '#FFFFFF',
        borderRadius: 12,
        padding: 16,
        margin: 8,
        alignItems: "center",
        shadowColor: '#000000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 4,
        elevation: 3,
    },

    title: {
        fontSize: 16,
        fontFamily: FONTS.montserrat.bold,
        textAlign: "center",
        marginBottom: 8,
        color: '#333333',
    },

    totalText: {
        fontSize: 18,
        fontFamily: FONTS.openSans.semiBold,
        color: '#007AFF',
        marginBottom: 16,
    },

    loadingText: {
        marginTop: 12,
        color: '#666666',
    },

    errorText: {
        color: '#D32F2F',
        textAlign: "center",
    },

    emptyText: {
        color: '#999999',
        textAlign: "center",
    },

    chartContainer: {
        height: 250,
        width: 250,
        position: "relative",
        justifyContent: "center",
        alignItems: "center",
    },

    chartWrapper: {
        height: 250,
        width: 250,
        position: "absolute",
        top: 0,
        left: 0,
    },

    legend: {
        marginTop: 20,
        width: '100%',
    },

    legendItem: {
        flexDirection: "row",
        alignItems: "center",
        marginBottom: 8,
        paddingVertical: 8,
    },

    legendColor: {
        width: 16,
        height: 16,
        borderRadius: 4,
        marginRight: 8,
    },

    legendText: {
        fontSize: 14,
        fontFamily: FONTS.openSans.semiBold,
        color: '#666666',
        flex: 1,
    },

    tooltipCentro: {
        position: "absolute",
        top: '50%',
        left: '50%',
        transform: [{ translateX: -80 }, { translateY: -80 }],
        width: 160,
        height: 160,
        backgroundColor: '#000000F2',
        padding: 20,
        borderRadius: 80,
        alignItems: "center",
        justifyContent: "center",
        shadowColor: '#000000',
        shadowOffset: { width: 0, height: 4 },
        shadowOpacity: 1,
        shadowRadius: 10,
        elevation: 15,
        zIndex: 1000,
        borderWidth: 2,
        borderColor: '#FFFFFF20',
    },

    tooltipTitle: {
        color: '#FFFFFF',
        fontFamily: FONTS.montserrat.semiBold,
        fontSize: 16,
        marginBottom: 6,
        textAlign: "center",
    },

    tooltipTextSmall: {
        color: '#FFFFFF',
        fontSize: 16,
        textAlign: "center",
        marginBottom: 2,
        fontFamily: FONTS.openSans.semiBold,
    },

    tooltipText: {
        color: '#FFFFFF',
        fontSize: 16,
        textAlign: "center",
        marginBottom: 2,
    },

    tooltipColor: {
        width: 20,
        height: 20,
        borderRadius: 10,
        marginBottom: 6,
    },

    highlightedLegendItem: {
        backgroundColor: '#4ECDC41A',
        borderRadius: 6,
        padding: 4,
    },

    instructionText: {
        fontSize: 12,
        color: '#666666',
        marginTop: 8,
        fontFamily: FONTS.openSans.semiBold,
        marginBottom: 8,
        textAlign: "center",
    },

});