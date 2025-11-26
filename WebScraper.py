#SET UP

# Imports
from selenium import webdriver
from selenium.webdriver.common.by import By
from joblib import Parallel, delayed
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time

options = webdriver.ChromeOptions()
options.add_argument("--incognito")

driver = webdriver.Chrome(options=options)
driver.get("https://2014.5e.tools/bestiary.html#aarakocra_mm")

try:
    elem = WebDriverWait(driver, 30).until(
    EC.presence_of_element_located((By.ID, "list")) #This is a dummy element
    )
    element = driver.find_element(By.ID, "list")
    print(element.get_attribute("xpath"))
finally:
    driver.close()