require 'watir-webdriver'
b = Watir::Browser.new
b.goto 'http://localhost:8081/numbrcrunchr'

b.text.include? 'NumbrCrunchr - Investment Property Number Cruncher'
b.text.include? 'Step 1 : Property Details'
b.text.include? 'Step 2 : Income Details'
b.text.include? 'Investment Summary'
b.text.include? '+ Change Assumptions'

b.select_list(:id => 'feasibilityAnalysisForm:State').select 'NSW'
b.text_field(:id => 'feasibilityAnalysisForm:Price').set '320000'
b.text_field(:id => 'feasibilityAnalysisForm:WeeklyRent').set '320'
b.button(:id => 'feasibilityAnalysisForm:CrunchNumbers').click

b.text.include? "Lender's mortgage insurance may be applicable"
b.text.include? "Select Download to download the raw data"
b.text.include? "Based on the information you provided, this property will be Cashflow Positive in 2020. To download the analysis, select Download Excel or Download CSV"
