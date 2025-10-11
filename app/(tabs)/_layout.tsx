import { Tabs } from 'expo-router';
import {Footer} from '@/src/components/Footer';

export default function TabLayout() {
    return (
        <>
        <Tabs
            tabBar={() => <Footer />}
            screenOptions={{ headerShown: false }}
        >
            <Tabs.Screen name="home" />
            <Tabs.Screen name="historyTransactions" />
            <Tabs.Screen name="add" />
            <Tabs.Screen name="charts" />
            <Tabs.Screen name="settings" />
        </Tabs>
        </>
    );
}