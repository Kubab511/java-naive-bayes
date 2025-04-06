import pandas as pd

data = pd.read_csv("ChildIsEnrolled_Predictive.csv")

freq_table = data.value_counts().reset_index(name="Frequency")

print(freq_table)