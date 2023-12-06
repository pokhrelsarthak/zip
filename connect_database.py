from cassandra.cluster import Cluster
from cassandra.auth import PlainTextAuthProvider
import time
import random
from datetime import datetime, timedelta
import pytz

# Cassandra Astra details
ASTRA_CLIENT_ID = "SZheMmqpZfkKISMzGHgzrqNA"
ASTRA_CLIENT_SECRET = "XOya30OLyLew9HdKgubezUddj77vTHT2x-v2d51TfZ,LbdTvYkZ7,O2oCW8AhWG9h6OexZkZ7PUR0JsPyKHgPE56wC.ffSPwfik0u+1z.FGtx0zl_gY.B3,eLawJZeNe"
ASTRA_SECURE_CONNECT_BUNDLE_PATH = "C:\\ConnectBundle\\secure-connect-agripriceanalysis.zip"
ASTRA_KEYSPACE = "agri_keyspace"
TABLE_NAME = "fruitables"

# Connect to Astra database
cloud_config = {
    'secure_connect_bundle': ASTRA_SECURE_CONNECT_BUNDLE_PATH
}

auth_provider = PlainTextAuthProvider(ASTRA_CLIENT_ID, ASTRA_CLIENT_SECRET)
cluster = Cluster(cloud=cloud_config, auth_provider=auth_provider)
session = cluster.connect()

fruits = ['apple', 'banana', 'papaya']

# to store the history of prices of fruits
prev_prices = {}

# Update existing rows with timestamp for each fruit every 10 seconds
color = ''
for i in range(8):

    for fruit in fruits:
        current_price = round(random.uniform(10, 50), 2)  # Random current price
        
        # Conversion of default utc to local ist time format
        current_utc_time = datetime.utcnow()
        utc_timezone = pytz.timezone('UTC')
        ist_timezone = pytz.timezone('Asia/Kolkata')
        ist_time = utc_timezone.localize(current_utc_time).astimezone(ist_timezone)
        curtime = ist_time.strftime('%Y-%m-%d %H:%M:%S')

        # Assigning color to each record, based on the price change
        # (green - price decrease, red - price increase, gray - no change in price)
        if fruit not in prev_prices:
            change_in_price = str(0.00) + "% "
            prev_prices[fruit] = current_price
            color = 'Gray'
        else:
            x = ((current_price - prev_prices[fruit])/ prev_prices[fruit]) * 100
            change_in_price = round(x, 2)
            if change_in_price > 0:
                color = 'red'
            elif change_in_price < 0:
                color = 'green'
            else:
                color = 'Gray'
            change_in_price  = str(change_in_price) + ' %'

        # Initializing Query to insert data into cassandra DB (Astrax db)
        query = f"INSERT INTO {ASTRA_KEYSPACE}.{TABLE_NAME} (name, time, price_change, current_price, prev_price, color) VALUES ('{fruit}', '{curtime}', '{change_in_price}', {current_price}, {prev_prices[fruit]}, '{color}')"
        
        # executing the above query
        session.execute(query)

        prev_prices[fruit] = current_price

    time.sleep(10)  # Insert data every 10 seconds
