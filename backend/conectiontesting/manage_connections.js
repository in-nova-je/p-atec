const axios = require('axios');

const baseUrl = 'http://localhost:8080';
const bearerToken = 'eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzdHVkZW50IiwiZXhwIjoxNzcxMzYwMTE3LCJpYXQiOjE3NzEzNTMxMTcsInNjb3BlIjoiVVNFUiJ9.RhFfTgvL3cL9kYQcgavD8QhC4jvcFpdySuCcKMFXoZ5WduJxtUg9RMIgZs-fZS57A5EVExAB5fUKF6O3WG8SzazTTbwq9m2rxgYXljvpQLAQt14i2maUi0BV4AnyqC6rwsRIhPrwlq_uGafAW4DzYc0KEieBHyN-rlwZR_xdTJzTo0t9mC2NxO5zWDOe0CeqviWTmxKqq5tJJv-uUWJT1cdxmMlVTGFXKpV6BYjbstxGX-yQcRGxX6evU1plZNPPcJO0bschPQPBXd0Q8HmEpTXzsVXvwAcZyY5mBCNJDiolinFsItM8-1-L9C_HaM6NhWObYL6B0aEVj7zw7vMqTQ';

const headers = {
  'Authorization': `Bearer ${bearerToken}`,
  'Content-Type': 'application/json'
};

// Step 1: Create 100 connections
async function createConnections() {
  console.log('🔄 Creating 100 connections...\n');
  const createdIds = [];
  
  for (let i = 1; i <= 100; i++) {
    try {
      const response = await axios.post(
        `${baseUrl}/api/connections?userid=7&enterpriseid=2&isInternshipNoJob=true&classname=turmad`,
        {},
        { headers }
      );
      
      const connectionId = response.data.id || response.data;
      createdIds.push(connectionId);
      
      if (i % 10 === 0) {
        console.log(`✅ Created ${i}/100 connections...`);
      }
    } catch (error) {
      console.error(`❌ Error creating connection ${i}:`, error.response?.data || error.message);
    }
  }
  
  console.log(`\n✅ Successfully created ${createdIds.length} connections`);
  console.log(`📝 IDs range: ${Math.min(...createdIds)} - ${Math.max(...createdIds)}\n`);
  
  return createdIds;
}

// Step 2: Get all connections
async function getAllConnections() {
  console.log('📥 Fetching all connections...\n');
  
  try {
    const response = await axios.get(
      `${baseUrl}/api/connections?pageSize=100&pageNumber=0`,
      { headers }
    );
    
    const connections = response.data;
    console.log(`✅ Retrieved ${connections.length} connections\n`);
    console.log('📋 Connections:');
    console.log(JSON.stringify(connections, null, 2));
    console.log('\n');
    
    return connections;
  } catch (error) {
    console.error('❌ Error fetching connections:', error.response?.data || error.message);
    return [];
  }
}

// Step 3: Delete newly created connections (IDs > 9)
async function deleteConnections(createdIds) {
  console.log('🗑️  Deleting newly created connections...\n');
  
  // Filter to only delete IDs > 9 (the ones we just created)
  const idsToDelete = createdIds.filter(id => id > 9);
  
  console.log(`🎯 Will delete ${idsToDelete.length} connections (IDs > 9)`);
  console.log(`📝 IDs to delete: ${idsToDelete.join(', ')}\n`);
  
  let successCount = 0;
  let failCount = 0;
  
  for (const id of idsToDelete) {
    try {
      await axios.delete(
        `${baseUrl}/api/connections?id=${id}`,
        { headers }
      );
      successCount++;
      
      if (successCount % 10 === 0) {
        console.log(`✅ Deleted ${successCount}/${idsToDelete.length} connections...`);
      }
    } catch (error) {
      console.error(`❌ Error deleting connection ${id}:`, error.response?.data || error.message);
      failCount++;
    }
  }
  
  console.log(`\n✅ Successfully deleted ${successCount} connections`);
  if (failCount > 0) {
    console.log(`❌ Failed to delete ${failCount} connections`);
  }
}

// Main execution
async function main() {
  console.log('🚀 Starting connection management script\n');
  console.log('='.repeat(60));
  console.log('\n');
  
  try {
    // Step 1: Create 100 connections
    const createdIds = await createConnections();
    
    // Wait a bit to ensure all are created
    console.log('⏳ Waiting 2 seconds...\n');
    await new Promise(resolve => setTimeout(resolve, 2000));
    
    // Step 2: Get all connections
    const allConnections = await getAllConnections();
    
    // Wait a bit before deleting
    console.log('⏳ Waiting 2 seconds before deletion...\n');
    await new Promise(resolve => setTimeout(resolve, 2000));
    
    // Step 3: Delete newly created connections
    await deleteConnections(createdIds);
    
    console.log('\n' + '='.repeat(60));
    console.log('✅ Script completed successfully!');
    
  } catch (error) {
    console.error('\n❌ Script failed:', error.message);
  }
}

// Run the script
main();
