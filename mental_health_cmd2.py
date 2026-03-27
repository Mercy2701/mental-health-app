import sys
import pandas as pd
import joblib

# =========================
# VALIDASI ARGUMEN
# =========================
if len(sys.argv) != 14:
    print("Usage: python mental_health_cmd2.py "
          "<selfemployed> <num_employees> <tech_company> <tech_role> "
          "<previous_employers> <family_history> "
          "<sought_treatment> <age> <gender> <age_group> "
          "<work_position> <remote_work> "
          "<open_about_mental_health>")
    sys.exit(1)

# =========================
# LOAD MODEL & PREPROCESSOR
# =========================
model = joblib.load("decision_tree_model.pkl")
encoders = joblib.load("categorical_encoders.pkl")
imputer = joblib.load("imputer.pkl")
feature_columns = joblib.load("feature_columns.pkl")

# =========================
# INPUT USER
# =========================
input_data = {
    'Are you selfemployed': sys.argv[1] == "True",
    'How many employees does your company or organization have': sys.argv[2],
    'Is your employer primarily a tech companyorganization': sys.argv[3] == "True",
    'Is your primary role within your company related to techIT': sys.argv[4] == "True",
    'Do you have previous employers': sys.argv[5] == "True",
    'Do you have a family history of mental illness': sys.argv[6],
    'Have you ever sought treatment for a mental health issue from a mental health professional': sys.argv[7] == "True",
    'What is your age': float(sys.argv[8]),
    'What is your gender ': sys.argv[9],
    'Age Group': sys.argv[10],
    'Which of the following best describes your work position': sys.argv[11],
    'Do you work remotely': sys.argv[12],
    'Question about speaking openly about mental health vs physical health': sys.argv[13],
}  

# =========================
# CONVERT TO DATAFRAME
# =========================
input_df = pd.DataFrame([input_data])

# =========================
# APPLY IMPUTER (WAJIB sebelum encode)
# =========================
input_df = pd.DataFrame(
    imputer.transform(input_df),
    columns=input_df.columns
)

# =========================
# APPLY ENCODER
# =========================
for col, encoder in encoders.items():
    if col in input_df.columns:
        input_df[col] = input_df[col].map(encoder)

        if input_df[col].isnull().any():
            print("WARNING: value tidak ditemukan di encoder untuk kolom:", col)

        input_df[col] = input_df[col].fillna(0).astype(int)

# =========================
# SAMAKAN STRUKTUR DENGAN TRAINING
# =========================
input_df = input_df.reindex(columns=feature_columns, fill_value=0)
# NORMALISASI TEKS
for col in input_df.columns:
    if input_df[col].dtype == "object":
        input_df[col] = input_df[col].str.strip().str.lower()

# =========================
# PREDICTION
# =========================
prediction = model.predict(input_df)

# =========================
# OUTPUT (FIX UTAMA DI SINI)
# =========================
print(prediction[0])